rsync -avz --chmod=a+rwx,g+rwx,o+rwx -e="ssh -i %USERPROFILE%/.ssh/aws1.pem" target/DropbikeBackend-0.1 ubuntu@ec2-54-69-186-125.us-west-2.compute.amazonaws.com:/var/lib/tomcat7/webapps/