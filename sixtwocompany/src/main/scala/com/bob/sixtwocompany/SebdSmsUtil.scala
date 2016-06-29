package com.bob.sixtwocompany

import okhttp3.{MediaType, RequestBody, Request, OkHttpClient}
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write

case class SMSBody(content: String, targetInfos: List[String], contentType: String = "SMS", highPriority: Boolean = true, messageType: String = "SMS", sendTime: Int = -1)

object SebdSmsUtil {

  val proAddress = "http://msg.51.nb/messageservice/send.do"
  val testAddress = "http://192.168.2.66:8080/messageservice/send.do"
  val okhttp: OkHttpClient = new OkHttpClient();


  implicit val formats = Serialization.formats(NoTypeHints)

  /**
   * 下面c#代码的翻译
   * @param mobile
   * @param checkcode 验证码
   * @param userid
   * @param modelNo
   * @param appId
   */
  def send(mobile: String, checkcode: String, userid: Int = 0, modelNo: Int = 1, appId: Int = 1) = {

    val targetInfo = if (userid == 0) s"""{"mobile":"${mobile}"}""" else s"""{"mobile":"${mobile}","userId":${userid}}"""
    val targetInfos = List(targetInfo)
    val content = s"""{"appId":${appId},"data":"[${checkcode}]","modelNo":"${modelNo}","type":"51checkcode"}"""
    val smsBody = SMSBody(content, targetInfos)
    val jsonBody = write(smsBody)
    val request = new Request.Builder()
      .url(testAddress)
      .post(RequestBody.create(MediaType.parse("text/html; charset=utf-8"), jsonBody))
      .build()
    val response = okhttp.newCall(request).execute()
    val res = pretty(render(parse(response.body().string())))
    val resbody = if (response.isSuccessful) s"sussess -- ${res}" else s"error --- ${res}"
    println(resbody)
  }
}

/*
/// <summary>
        /// 给指定手机发送验证码--item1:batchno; item2:error
        /// </summary>
        /// <param name="mobile"></param>
        /// <param name="checkcode">短信验证码</param>
        /// <param name="userid"></param>
        /// <param name="modelNo">1 具体值可咨询黄川--短信网关同事</param>
        /// <returns></returns>
        public static Tuple<string, string> SendSmsNew(this string mobile, string checkcode, long userid = 0, long modelNo = 1)
        {
            string posturl = System.Configuration.ConfigurationManager.AppSettings["sendsmsurl"];
            //posturl = "http://192.168.2.66:8080/messageservice/send.do";
            //posturl = "http://msg.51.nb/messageservice/send.do";
            if (string.IsNullOrWhiteSpace(posturl))
            {
                throw new Exception("配置文件缺少短信通知地址");
            }

            string targetInfo = string.Empty;
            if (userid != 0)
            {
                targetInfo = new { mobile = mobile, userId = userid }.ToJson();
            }
            else
            {
                targetInfo = new { mobile = mobile }.ToJson();
            }

            var obj = new
            {
                content = new { appId = 1, data = new List<object> { checkcode }, modelNo = modelNo, type = "51checkcode" }.ToJson(),
                contentType = "SMS",
                highPriority = true,
                messageType = "SMS",
                sendTime = -1,
                targetInfos = new List<string> { targetInfo },
            };

            string result = string.Empty;
            posturl.PostStringToUrl(requestBody: obj.ToJson(),
               contentType: "text/html",
               requestFilter: null,
               responseFilter: rs =>
               {
                   Stream newStream = rs.GetResponseStream();
                   StreamReader sr = new StreamReader(newStream, Encoding.GetEncoding("gbk"));
                   result = sr.ReadToEnd();
               });

            ServiceStack.Text.JsonObject jsobj = ServiceStack.Text.JsonObject.Parse(result);
            return new Tuple<string, string>(jsobj["batchNo"], jsobj["error"]);
        }
 */