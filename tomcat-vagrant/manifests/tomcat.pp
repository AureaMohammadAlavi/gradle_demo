class java_8::install {
  exec { "update-package-list":
    command => "/usr/bin/sudo /usr/bin/apt-get update",
  }

  package { "openjdk-8-jdk":
    ensure  => installed,
    require => Exec["update-package-list"]
  }

}

include java_8::install



class tomcat8::prerequisites {
  group { "tomcat":
    ensure => "present"
  }

  user { "tomcat":
    ensure     => "present",
    gid        => "tomcat",
    managehome => true,
    require    => Group['tomcat']
  }

  package { "unzip":
    ensure  => installed,
    require => Exec["update-package-list"]
  }
  package { "wget":
    ensure  => installed,
    require => Exec["update-package-list"]
  }
}



class h2 {
  include java_8::install
  include tomcat8::prerequisites

  exec { "run_database":
    command =>
      "/usr/bin/java -cp /vagrant/files/h2-1.4.200.jar org.h2.tools.Server -tcp -tcpPort 1300 -tcpPassword secret &"
    ,
    user    => 'tomcat'
  }

  exec { "create_schema":
    command =>
      "/usr/bin/java -cp /vagrant/files/h2-1.4.200.jar org.h2.tools.RunScript -url jdbc:h2:~/todo -script /vagrant/files/create-todo.sql"
    ,
    user    => 'tomcat',
    creates => "/home/tomcat/todo.h2.db"
  }
}

include h2


class tomcat8 {
  include tomcat8::prerequisites
  $tomcat_version = "8.5.50"
  $tomcat_name = "apache-tomcat-${tomcat_version}"
  $tomcat_download_url = "http://kozyatagi.mirror.guzel.net.tr/apache/tomcat/tomcat-8/v${
    tomcat_version}/bin/apache-tomcat-${tomcat_version}.zip"
  $tomcat_install_dir = "/home/tomcat/${tomcat_name}"

  exec {
    "download_tomcat8":
      cwd     => "/tmp",
      command => "/usr/bin/wget $tomcat_download_url",
      creates => "/tmp/${tomcat_name}";

    "unpack_tomcat8":
      cwd     => "/home/tomcat",
      command => "/usr/bin/unzip /tmp/${tomcat_name}",
      creates => "/home/tomcat/${tomcat_name}",
      require => Exec["download_tomcat8"];

    "make_executable":
      cwd     => "/home/tomcat/${tomcat_name}",
      command => "/bin/chmod +x bin/*.sh",
      require => Exec["unpack_tomcat8"];

    "soft_link":
      cwd     => "/home/tomcat",
      command => "/bin/ln -s ${tomcat_name} apache-tomcat",
      require => Exec["make_executable"];

    "change_owner":
      cwd     => "/",
      command => "/bin/chown -R tomcat:tomcat /home/tomcat",
      require => Exec["soft_link"]
  }
/*
  file { "/home/tomcat/${tomcat_name}/webapps/web-1.0.0.war":
    owner   => 'tomcat',
    group   => 'tomcat',
    source  => "/vagrant/files/web-1.0.0.war",
    notify  => Service['tomcat'],
    require => [ Exec['change_owner'] ]
  }*/

  service { 'tomcat':
    ensure     => running,
    start      => "sudo -u tomcat /home/tomcat/${tomcat_name}/bin/startup.sh",
    stop       => "sudo -u tomcat /home/tomcat/${tomcat_name}/bin/shutdown.sh",
    status     => "",
    restart    => "",
    hasstatus  => false,
    hasrestart => false,
    require    => [ Exec['unpack_tomcat8'], Class['tomcat8::prerequisites'] ],
  }

}
include tomcat8


Class["java_8::install"] -> Class["tomcat8"]
Class["java_8::install"] -> Class["h2"]
