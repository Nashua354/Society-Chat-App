from django.shortcuts import render
from django.http import HttpResponse, Http404, HttpResponseRedirect
from users.models import Users
from google.auth.transport import requests
from google.oauth2 import id_token



class Response:
    @staticmethod
    def json(data, status='success', status_message='done'):
        import json
        return HttpResponse(json.dumps({'status': status, 'status_message': status_message, 'data': data}))

def index(request):
    data = render(request, 'index.html')
    return HttpResponse(data)

def onlogin(request):
    if request.method != "POST": raise Http404
    error=""
    token = request.POST.get("id_token", "#")
    # print(token)
    try:
        print('check1')
        idinfo = id_token.verify_oauth2_token(token, requests.Request(), "67628674747-ngitfkigrvtclgfo0ot8r6teur3v7nkb.apps.googleusercontent.com")
        if idinfo['iss'] not in ['accounts.google.com', 'https://accounts.google.com']:
            print('Wrong issuer.')
            raise ValueError('Wrong issuer.')
        print('check2')
        userid = idinfo['sub']
        print("got the user id", userid, idinfo['email'])
        if Users.is_logged(request):
            if request.session.get('email', '#') != idinfo['email']:
                Users.logout(request)
        Users.create_account(idinfo['email'], userid)
        Users.login(request, idinfo['email'])
        error="Okay"
        '''except ValueError:
        print("Error occured"+str(ValueError))
        pass'''
    except Exception as e:
        print(str(e))
    print(error)
    return HttpResponse(error)


def usr_info(request):
    if not Users.is_logged(request):
        raise Http404
    return Response.json(Users.get_details(request.session['id']))


def head_socchat(request):
    return HttpResponseRedirect("/static/index.html")


def get_csrf(request):
    #print(request.META)
    return HttpResponse(render(request, "csrf.html"))


def get_logout(request):
    if request.method != "POST": raise Http404
    if Users.logout(request):
        return HttpResponse("Done")
    return HttpResponse("error occurred")


def log_out(request):
    del request.session['email']


def test(request):
    return Response.json(request.GET)


def check_login(request):
    if request.method != "POST":raise Http404
    try:
        if Users.is_logged(request):
            return HttpResponse("1")
    except:
            print("exception")
    return HttpResponse("0")

