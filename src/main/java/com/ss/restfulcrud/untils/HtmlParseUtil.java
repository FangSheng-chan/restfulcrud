package com.ss.restfulcrud.untils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ss.restfulcrud.entities.Content;
import com.ss.restfulcrud.entities.Zwjc;
import com.ss.restfulcrud.entities.Zzgg;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HtmlParseUtil {
    public static void main(String[] args) throws Exception {
        List<Zzgg> zzggListlist = new HtmlParseUtil().getzxgg("300307");
        for (Zzgg list : zzggListlist) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String announcementId = list.getAnnouncementId();
            String announceTime = sdf2.format(new Date(Long.parseLong(list.getAnnouncementTime())));
            String urlStr = "http://www.cninfo.com.cn/new/announcement/download?bulletinId=" + announcementId + "&announceTime=" + announceTime;
            downLoadByUrl(urlStr, "" + list.getSecName() + "-" + announceTime + ".pdf", "D:/pdf");
        }
    }


    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadByUrl(String urlStr, String fileName,
                                     String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(5 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        // 获取自己数组
        byte[] getData = readInputStream(inputStream);
        // 文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");

    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


    public List<Zzgg> getzxgg(String keyword) throws IOException {
        List<String> element = new HtmlParseUtil().parseZwjc(keyword);
        String e1 = element.get(0);
        String e2 = element.get(1);
        String s = e1 + "," + e2;
        String url = "http://www.cninfo.com.cn/new/hisAnnouncement/query?tabName=fulltext&column=szse&category=category_ndbg_szsh&plate=sz&seDate=2014-01-01~2019-12-31&isHLtitle=true&pageSize=30&pageNum=1&stock=" + s;
        String body = Jsoup.connect(url).ignoreContentType(true)
                .method(Connection.Method.POST).header("Content-Type", "application/json").timeout(3000).execute().body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONArray jsonArray = jsonObject.getJSONArray("announcements");
        String jsonString = jsonArray.toJSONString();
        List<Zzgg> list = JSONObject.parseArray(jsonString, Zzgg.class);
        ArrayList<Zzgg> zzggArrayList = new ArrayList<>();
        return list;
    }

    public List<String> parseZwjc(String keywords) throws IOException {
        ArrayList<String> arrayList = new ArrayList<>();
        String url = "http://www.cninfo.com.cn/new/information/topSearch/detailOfQuery?maxSecNum=10&maxListNum=5&keyWord=" + keywords;
        String body = Jsoup.connect(url).ignoreContentType(true)
                .method(Connection.Method.POST).header("Content-Type", "application/json").timeout(3000).execute().body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONArray jsonArray = jsonObject.getJSONArray("keyBoardList");
        String jsonString = jsonArray.toJSONString();
        List<Zwjc> list = JSONObject.parseArray(jsonString, Zwjc.class);
        String code = list.get(0).getCode();
        String orgId = list.get(0).getOrgId();
        arrayList.add(code);
        arrayList.add(orgId);
        return arrayList;
    }

    public List<Content> parseJD(String keywords) throws Exception {
        //  获取请求    https://search.jd.com/Search?keyword=java
        // 前提，需要联网
        String url = "https://search.jd.com/Search?keyword=" + keywords;
        //解析网页(Jsoup返回Document就是Document页面)
        Document document = Jsoup.parse(new URL(url), 30000);

        Element element = document.getElementById("J_goodsList");

        Elements elements = element.getElementsByTag("li");

        ArrayList<Content> goodsList = new ArrayList<>();

        for (Element el : elements) {
            // 关于图片特别多的网站，所有图片都是懒加载的！
            // source-data-lazy-img
            String img = el.getElementsByTag("img").eq(0).attr("src");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            String shopNum = el.getElementsByClass("p-shopnum").eq(0).text();
            String commit = el.getElementsByClass("p-commit").eq(0).text();

            Content content = new Content();
            content.setTitle(title);
            content.setPrice(price);
            content.setImg(img);
            content.setShopNum(shopNum);
            content.setCommit(commit);

            goodsList.add(content);

        }
        return goodsList;
    }
}
