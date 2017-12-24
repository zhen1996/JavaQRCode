# SalamanderQRCode
把zxing.org的源码拿过来，然后加了点高斯模糊的代码，做了二维码识别

# 使用
GET调用接口**/w/decode?u=your_qrcode_url**，返回JSON格式结果
```
{
    "errno": 0,
    "text": "https://u.wechat.com/EMsWWNbkxMPlwwQg2BCC8Zg"
}
```
1. **errno**为0，表示解析成功，其他为错误
2. **text**为解析出来的文本结果
