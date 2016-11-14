![image](https://github.com/AnyLifeZLB/Retrofit2.0_Demo/raw/master/banner.jpg)
#Retrofit 为什么需要再次封装

- api 不是那么的Restful  
- 统一请求过程中的处理
- http 错误处理  
## API 不是那么的Restful
我们知道github api（https://developer.github.com/v3） 是非常的restful 的风格，比如  
>List all organizations GET /organizations 这个请求返回
```
[
  {
    "login": "github",
    "id": 1,
    "url": "https://api.github.com/orgs/github",
    "repos_url": "https://api.github.com/orgs/github/repos",
    "events_url": "https://api.github.com/orgs/github/events",
    "hooks_url": "https://api.github.com/orgs/github/hooks",
    ... ...
  }
]
```
是不是很Restful，但是这样的格式有个弊端就是：当在浏览器调试api，后端查询出错时，很难查看错误码&错误信息。（当然用chrome的开发者工具可以看，但麻烦）

所以我们看见的http 数据返回格式一般是这样的：  
```
        {
            "code": 0, 
            "error": "",
            "result":
                   {
                       "access_token": "if i should see you after long years,how should i greet",
                       "token_type": "Bearer",
                       "expires": "2016-05-12 17:13:13",
                       "refresh_token": "with tear? with slience",
                       "scopes": "all"
                   }
        }
```  
这样的好处就是调试api方便，在任意浏览器都可以直观地看到错误码和错误信息

既然不是restful api 那么




