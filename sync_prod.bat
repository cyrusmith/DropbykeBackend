ERM rsync -avz --chmod=a+rwx,g+rwx,o+rwx -e="ssh -i %USERPROFILE%/.ssh/dropbike-prod-1.pem" target/DropbikeBackend-0.1 ubuntu@ec2-54-68-46-169.us-west-2.compute.amazonaws.com:/var/lib/tomcat7/webapps/