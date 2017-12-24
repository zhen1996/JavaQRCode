# SalamanderQRCode
把zxing.org的源码拿过来，然后加了点高斯模糊的代码，做了二维码识别

# 使用
### GET /w/decode?u=your_qrcode_url
正确返回
```
{
    "errno": 0,
    "text": "https://u.wechat.com/EMsWWNbkxMPlwwQg2BCC8Zg"
}
```
错误返回
```
{
    "errno":1,
    "text":"err: badimage"
}
```

1. **errno**为0，表示解析成功，其他为错误
2. **text**为解析出来的文本结果或错误信息
