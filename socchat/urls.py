"""socchat URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from django.conf import settings
from django.conf.urls import url, static
from django.urls import include
from .views import *

urlpatterns = [
    path('admin/', admin.site.urls),
    url(r'^checklogin', check_login),
    url(r'^signIn', onlogin),
    url(r'^get_val', get_csrf),
    url(r'^signOut', get_logout),
    url(r'^usrinfo', usr_info),
    url(r'^$', index),
    path('apiusers/', include('users.urls')),
    url(r'^profile', user_profile),
    url(r'search', search)
    
]
