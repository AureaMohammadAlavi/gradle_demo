environments {
    local {
        server {
            hostname = "192.168.33.10"
            sshPort = 22
            username = "vagrant"
            keyfile = '$projectDir/tomcat-vagrant/.vagrant/machines/tomcat/virtualbox/private_key'
        }
        tomcat {
            hostname = "192.168.33.10"
            port = 8080
        }
    }
    production {
        server {
            hostname = "127.0.0.1"
            sshPort = 2222
            username = "vagrant"
            keyfile = System.getenv().get("SERVER_PRIVATE_KEY_FILE")
        }
        tomcat {
            hostname = "192.168.33.10"
            port = 8080
        }
    }
}

