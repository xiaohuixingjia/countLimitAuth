按接口和商户号来限制商户的tps和每日最大访问笔数以及最大量的限制，
防止同意时刻某商户请求过多占用服务器过多资源，如果超频则直接拒绝该笔请求，
可以灵活的对接口和商户进行tps和日访问量的限制做配置，如果没有配置则取默认
未超频返回 0000
秒超频返回 0301
天超频返回 0302
总量超频返回 0303

访问 /configWriter 打印流量配置
访问 /configAlter 调整超频限制
<request>
  <funcode></funcode>
  <merid></merid>
  <limitType>tps</limitType>
  <maxValue>15</maxValue>
  <clear>false</clear>
</request>

其中 limitType为 tps,day,total 分别代表秒 天 总量
访问 /limitRes 判断总量是否超频
访问非以上请求路径则判断 tps和day是否超频


判断超频的请求报文：
<request>
  <funcode></funcode>
  <merid></merid>
</request>