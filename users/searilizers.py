from rest_framework import serializers
from .models import *

class UsersSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Users
        fields = ('url', 'email', 'gid')
        
