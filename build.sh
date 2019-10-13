echo "setting up environment"
. ./setup-java.sh
echo "Running build for docker"
mvn clean install -Ddocker
if [ $? -eq 0 ]; then
  echo "Build completed for docker"
  echo "Spinup testable components"
  docker-compose up &
  echo "Checking webservice health"
  siteUp=0;
  while [ ${siteUp} -eq 0 ]
  do
    status=`curl -Is http://localhost:9001/health | head -n 1|grep 200`
    if [ "$status"x != x ]
    then
      siteUp=1;
      echo "Service is up"
    else
      siteUp=0;
      echo "Service is not up yet"
      sleep 5s
    fi
  done;
  echo "Running tests"
  mvn -f integration-tests/pom.xml clean install
  kill %1
else
  echo "Build failed"
fi