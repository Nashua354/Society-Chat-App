from django.shortcuts import render
from rest_framework import viewsets, response, status
from .models import *
from .searilizers import *
# Create your views here.

class UserViewSet(viewsets.ModelViewSet):
    "API endpoint that allows only viewing the users"
    queryset = Users.objects.all()
    serializer_class = UsersSerializer
    http_method_names = ['get', 'delete']

    def list(self, request, format=None):
        '''if not Users.is_logged(request):
            return response.Response("Unauthorized", status=status.HTTP_203_NON_AUTHORITATIVE_INFORMATION)
        '''
        users = Users.objects.all()
        serializer = UsersSerializer(users, many=True, context={'request':request})
        return response.Response(serializer.data)
    