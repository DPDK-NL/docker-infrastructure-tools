FROM docker.io/jenkins/jenkins:lts-jdk17
ENV JENKINS_HOME /var/jenkins_home
ENV JAVA_OPTS="-Dio.jenkins.plugins.casc.ConfigurationAsCode.initialDelay=9000 \
  -Djenkins.install.runSetupWizard=false \
  -Dorg.jenkinsci.main.modules.sshd.SSHD.hostName=127.0.0.1"
ENV SECRETS /var/jenkins_home/
USER root
RUN echo 'DPkg::Post-Invoke {"/bin/rm -f /var/cache/apt/archives/*.deb || true";};' \
  | tee /etc/apt/apt.conf.d/clean \
  && apt-get update && apt-get install -y lsb-release \
  && curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
  https://download.docker.com/linux/debian/gpg \
  && echo "deb [arch=$(dpkg --print-architecture) \
  signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
  https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list \
  && apt-get update && apt-get install -y docker-ce-cli
USER jenkins
COPY files/plugins.txt /tmp/plugins.txt
RUN jenkins-plugin-cli -f /tmp/plugins.txt 
ADD files/init.groovy.d /usr/share/jenkins/ref/init.groovy.d
ADD files/dsl /usr/share/jenkins/ref/dsl
COPY files/scriptApproval.xml /var/jenkins_home/script/scriptApproval.xml
