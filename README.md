# SalamanderQRCode
把zxing.org的源码拿过来，然后加了点高斯模糊的代码，做了二维码识别

# 使用
```
docker run -it --rm -p 8888:8080 salamandermh/salamanderqrcode
```

# API

## GET /w/decode
## 参数
|参数名|必选|类型|说明|
|--- |--- |--- |--- |
|u|是|string|二维码图片链接|

## 返回示例
```
解析成功
{
    "errno": 0,
    "text": "https://u.wechat.com/EMsWWNbkxMPlwwQg2BCC8Zg"
}
解析错误
{
    "errno":1,
    "text":"err: badimage"
}
```
## 返回参数说明
|参数名|类型|说明|
|--- |--- |--- |
|errno|int|错误码，0：成功；1：失败|
|text|string|解析信息：解析成功时为二维码对应的文本信息，解析错误时为错误信息|


## POST /w/decode
## 参数
|参数名|必选|类型|说明|
|--- |--- |--- |--- |
|f|是|file|二维码图片|

## 返回示例
```
解析成功
{
    "errno": 0,
    "text": "https://u.wechat.com/EMsWWNbkxMPlwwQg2BCC8Zg"
}
解析错误
{
    "errno":1,
    "text":"err: badimage"
}
```
## 返回参数说明
|参数名|类型|说明|
|--- |--- |--- |
|errno|int|错误码，0：成功；1：失败|
|text|string|解析信息：解析成功时为二维码对应的文本信息，解析错误时为错误信息|
