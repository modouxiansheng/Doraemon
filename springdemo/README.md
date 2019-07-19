# SpringBoot 2.0 多图片上传加回显

这两天公司有需求让做一个商户注册的后台功能，其中需要商户上传多张图片并回显。由于之前没做过这方面的东西，此篇文章用以记录一些知识点，以便后续查看。


## 上传

Controller的代码非常简单，由于用了SpringMVC框架，所以直接用`MultipartFile`来接即可。由于是多图片上传所以用**数组**来接。**此处应该注意参数名应该和`<input>`中的`name`值相对应**

```
@RequestMapping("/pic")
@ResponseBody
public ResponseEntity<String> pic(MultipartFile [] pictures) throws Exception {
    ResponseEntity<String> responseEntity = new ResponseEntity<>();
    long count = Arrays.asList(pictures).stream().
            map(MultipartFile::getOriginalFilename).
            filter(String::isEmpty).count();
    if (count == pictures.length){
        responseEntity.setCode(ResponseEntity.ERROR);
        throw new NullOrEmptyException("图片不能同时为空");
    }
    responseEntity.setCode(ResponseEntity.OK);
    responseEntity.setMessage("上传成功");
    return responseEntity;
}
```

前端页面的代码，**此处的`name`值和`Controller`的参数名称是对应的**

```
<div class="container">
    <div class="avatar-upload">
        <div class="avatar-edit">
            <input type='file' name="pictures" id="imageOne" accept=".png, .jpg, .jpeg"/>
            <label for="imageOne"></label>
        </div>
        <div class="avatar-preview">
            <div id="imageOnePreview"
                 style="background-image: url(http://ww3.sinaimg.cn/large/006tNc79ly1g556ca7ovqj30ak09mta2.jpg);">
            </div>
        </div>
    </div>
</div>

```

js代码回显

```
function readURLOne(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $('#imageOnePreview').css('background-image', 'url('+e.target.result +')');
            $('#imageOnePreview').hide();
            $('#imageOnePreview').fadeIn(650);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#imageOne").change(function() {
    readURLOne(this);
});

```

js代码上传

```
function getUpload(){
    //获取form表单中所有属性  key为name值
    var formData = new FormData($("#picForm")[0]);
    $.ajax({
        url: '/pic',
        type: 'POST',
        dataType:"json",
        data: formData,
        processData: false,
        contentType: false,
        success:(function(data) {
            window.confirm(data.message);
            window.location.reload();
        }),
        error:(function(res) {
            alert("失败");
        })
    });
}

```

## 效果展示

初始页面如下

![](http://ww2.sinaimg.cn/large/006tNc79ly1g559keji41j30x20kkwhk.jpg)

上传完图片以后回显为

![](http://ww3.sinaimg.cn/large/006tNc79ly1g559l6ymckj313s0niq9w.jpg)

点击提交以后可将图片上传至后台

## 配置上传图片的属性

默认情况下只允许上传1MB以下的图片，如果要设置上传图片大小。那么需要在配置文件中如下配置

```
spring:
  servlet:
    multipart:
      enabled: true
      max-file-size: 20MB
      max-request-size: 20MB
```

关于文件的配置有下面几个

```
spring.servlet.multipart.enabled=true # 是否支持多文件上传
spring.servlet.multipart.file-size-threshold=0B # 文件写入磁盘的阈值
spring.servlet.multipart.location= # 上传文件的保存地址
spring.servlet.multipart.max-file-size=1MB # 上传文件的最大值
spring.servlet.multipart.max-request-size=10MB # 请求的最大值
spring.servlet.multipart.resolve-lazily=false # 是否在文件或参数访问时延迟解析多部分请求


```

## 异常处理

异常处理用了Springboot提供的全局异常处理机制。只需要在类上加入`@ControllerAdvice`注解即可。在方法上加入`@ExceptionHandler(想要拦截的异常类)`就能拦截所有`Controller`的异常了。如果想要拦截指定为特定的`Controller`只需要在`@ControllerAdvice(basePackageClasses=想要拦截的Controller)`

```
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullOrEmptyException.class)
    @ResponseBody
    public ResponseEntity<String> nullOrEmptyExceptionHandler(HttpServletRequest request, NullOrEmptyException exception){
        log.info("nullOrEmptyExceptionHandler");
        return handleErrorInfo(request, exception.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> defaultErrorHandler(HttpServletRequest request, Exception exception){
        log.info("defaultErrorHandler");
        return handleErrorInfo(request, exception.getMessage());
    }

    private ResponseEntity<String> handleErrorInfo(HttpServletRequest request, String message) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>();
        responseEntity.setMessage(message);
        responseEntity.setCode(ResponseEntity.ERROR);
        responseEntity.setData(message);
        responseEntity.setUrl(request.getRequestURL().toString());
        return responseEntity;
    }
}

```

## 遇到的坑

* 如果返回值是模板文件的文件名，那么无论是类上还是方法上都不能加`@ResponseBody`注解，因为如果加了的话会被解析成Json串返回。
* 注意前端所传参数名和后端接收参数名一一对应。不然会报**405错误**
* 使用IDEA开发如果使用了`lombok`那么需要在`Annotation Processors`中将`Enable annotation processing`打对勾
	
	![](http://ww3.sinaimg.cn/large/006tNc79ly1g55a28y6iyj311q0l3432.jpg)
