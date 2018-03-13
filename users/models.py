from django.db import models
import json


class Users(models.Model):
    email = models.EmailField(max_length=80, null=False, unique=True)
    gid = models.CharField(max_length=100, null=True)
    name = models.CharField(max_length=30)
    profile = models.TextField()

    def __str__(self):
        return self.email

    @staticmethod
    def create_account(email, gid):
        if Users.objects.filter(email=email).count() >=1:
            return False
        usr = Users()
        usr.email = email
        usr.gid = gid
        usr.save()
        return True

    @staticmethod
    def login(request, email):
        if Users.objects.filter(email=email).count() !=1:
            return False
        # request.session['email'] = email
        request.session['id'] = Users.objects.get(email=email).id
        return True

    @staticmethod
    def logout(request):
        # del request.session['email']
        del request.session['id']
        return True

    @staticmethod
    def is_logged(request):
        if request.session.get("id", "#") != "#":
            return True
        return False

    @staticmethod
    def get_details(usrid):
        data={}
        usr = Users.objects.get(id=usrid)
        data['email']=usr.email
        data['gid'] = usr.gid
        data['api'] = "AIzaSyArxj2lGIhAsB0BTrWZxLYj3GF2q25Jaks"
        return data

    @staticmethod
    def get_profile(request):
        id = request.session.get('id')
        print(id)
        usr = Users.objects.get(id=id)
        return usr.profile

    @staticmethod
    def set_profile_detail(request):
        '''
            POST:{'set_profile':[['key','val'],['key','val'],['','']....]]}
        '''
        id = request.session['id']
        gotprof=request.POST.get("set_profile")
        values = json.loads(gotprof)
        usr = Users.objects.get(id=id)
        if usr.profile=="":
            usr.profile=gotprof
            usr.save()
            return
        data = json.loads(usr.profile+"")
        for v in values:
            data[v]=values[v]
        usr.profile = json.dumps(data)
        usr.save()

    @staticmethod
    def search(data):
        data2=[]
        users = Users.objects.all()
        for usr in users:
            val={}
            if usr.email.count(data)>0:
                val['id']=usr.id
                if not usr.profile == "":
                    print(usr.profile)
                    profile = json.loads(usr.profile)
                    val['profile']=profile
                else: val['profile']={}
                data2.append(val)
            else:
                if not usr.profile == "":
                    print(usr.profile)
                    profile = json.loads(usr.profile)
                    for key in profile:
                        if profile[key].count(data)>0:
                            val['id']=usr.id
                            val['profile']=profile
                            data2.append(val)
                            break
        return json.dumps(data2)
