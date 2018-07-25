. ~/.bash_profile

export LANG=zh_CN.UTF-8
FILE_PATH=/home/webfocus/huaxiaoqiang/applications/Dps-da-http-new
cd ${FILE_PATH}

MainClass=com.umpay.dpsda.server.DaletServer
SERVICE_ID=new_http_da_dps

APPCLASSPATH=
APPCLASSPATH=$APPCLASSPATH:.
APPCLASSPATH=$APPCLASSPATH:bin
APPCLASSPATH=$APPCLASSPATH:resource
for jarfile in `ls -1 lib/*.jar`
do
    APPCLASSPATH="$APPCLASSPATH:$jarfile"
done



pid=`ps -wwef|grep "Dflag=${SERVICE_ID}"|grep -v grep`

if [ -n "${pid}" ]
then
    echo "${SERVICE_ID} already start."
else
    nohup java -Xms512m -Xmx512m -Xmn64m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:"./log/gc-server.lo
g
" -cp ${APPCLASSPATH} -Dflag=${SERVICE_ID} ${MainClass} > /dev/null 2>&1 &
    echo $! > server.pid
fi
