from google.oauth2 import id_token
from google.auth.transport import requests
from users.models import *

def getVals(request):
        token = request.POST.get("id_token", "#")
        try:
            idinfo = id_token.verify_oauth2_token(token, requests.Request(), "596099888829-6vvcos64bnlgs0nrltala1id8g1k40hb.apps.googleusercontent.com")
            
            if idinfo['iss'] not in ['accounts.google.com', 'https://accounts.google.com']:
                raise ValueError('Wrong issuer.')

            userid = idinfo['sub']
            print("got the user id", userid, idinfo['email'])

            if Users.is_logged(request):
                if request.session.get('email', '#') != idinfo['email']:
                    Users.logout(request)
            Users.create_account(idinfo['email'], userid)
            Users.login(request, idinfo['email'])
            error="Okay"
        except ValueError:
            pass
        return HttpResponse(error)
