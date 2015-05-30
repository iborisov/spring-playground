#!/bin/sh

JMX_PORT=39999
JMX_RMI_PORT=39998

echo "You can connect to JMX through SSH tunnelling:"
echo "1) Setup SSH-tunnels for both ports:"
echo "ssh -N -v -L $JMX_RMI_PORT:localhost:$JMX_RMI_PORT -L $JMX_PORT:localhost:$JMX_PORT <YOUR_HOST>"
echo "2) Connect to JMX server with, e.g., JConsole:"
echo "jconsole service:jmx:rmi://localhost:$JXM_RMI_PORT/jndi/rmi://localhost:$JMX_PORT/jmxrmi"

JVM_ARGUMENTS="\
-server \
-Djava.net.preferIPv4Stack=true \
-XX:PermSize=64m \
-XX:MaxPermSize=128m \
-Xms256m \
-Xmx256m \
-XX:+UseConcMarkSweepGC \
-XX:+CMSClassUnloadingEnabled \
-Djava.rmi.server.hostname=localhost \
-Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=$JMX_PORT \
-Dcom.sun.management.jmxremote.rmi.port=$JMX_RMI_PORT \
-Dcom.sun.management.jmxremote.local.only=true \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false"

join() {
    local delim=$1      # join delimiter
    shift
    local oldIFS=$IFS   # save IFS, the field separator
    IFS=$delim
    local result="$*"
    IFS=$oldIFS   # restore IFS
    echo $result
}

## Spring Boot Maven plugin needs comma separated list of arguments for its run.arguments
ARGS=$(join , ${@})

COMMAND="mvn -Dmaven.test.skip clean spring-boot:run -Drun.jvmArguments=\""${JVM_ARGUMENTS}"\" -Drun.arguments=\""${ARGS}"\""

echo "Run command: " $COMMAND
eval $COMMAND
