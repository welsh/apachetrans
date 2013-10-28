# Reviewed: 
#	http://stackoverflow.com/questions/880227/what-is-the-minimum-i-have-to-do-to-create-an-rpm-file
# 	http://www.redhat.com/archives/rpm-list/2001-December/msg00200.html
# 	http://tecadmin.net/create-rpm-of-your-own-script-in-centosredhat/#
#	


%define        __spec_install_post %{nil}
%define          debug_package %{nil}
%define        __os_install_post %{nil} 

Name: apachetrans
Version: 1.1	
Release: 1
Summary: Installs the apachetrans connector	

Group: Application/Communications
License: GPL v2
URL: https://github.com/welsh/apachetrans
Source0: %{name}-%{version}.tar.gz

BuildArch: noarch
BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-root

%description
%{summary}

%prep
%setup -q

%build
rm -rf %{buildroot}
mkdir -p  %{buildroot}

%install
cp -a * %{buildroot}

%clean
rm -rf %{buildroot}

%files
%defattr(-,root,root,-)
%config(noreplace) /etc/apachetrans/application.conf
%config(noreplace) /var/log/apachetrans/output.log
/etc/init.d/apachetrans.sh
/usr/share/apachetrans/apachetrans.jar

%changelog
* Sun Oct 27 2013 David Welsh <david.welsh@welshh.com> 1.0-1
- Initial RPM Creation

* Sun Oct 27 2013 David Welsh <david.welsh@welshh.com> 1.1-1
- Updating to 1.1 of apachetrans
