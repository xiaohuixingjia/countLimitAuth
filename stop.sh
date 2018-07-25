pid=$(cat server.pid)
echo $pid
kill -15 $pid 
