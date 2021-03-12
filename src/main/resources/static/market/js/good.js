let goods = (
    function (){
        let request =new XMLHttpRequest();
        function getJson(){
            let json;
            request.onreadystatechange = function (){
                if (request.readyState==4){
                    if (request.status == 200){
                        json = request.responseText;
                    }else{
                        alert("网络异常")
                    }
                }
            }
            request.open("GET","/goods/base/get",false)
            request.send();
            return JSON.parse(json).data;
        }

        function addElement(json){
            let goodContain = document.getElementById("goods");
            console.log(json)
            let good_container = document.createElement("div");
            for(let x = 0;x<json.length;x++){
                let a = document.createElement("a");
                a.href = "...目标商品详情地址...."+json[x].id;
                let div = document.createElement("div");
                let imgDiv = document.createElement("div");
                let textDiv = document.createElement("div");
                let text = document.createElement("label");
                let img = document.createElement("img");
                let urls = json[x].fileUrl.split(";",2);
                img.src = urls[0];
                imgDiv.append(img);
                text.innerText = json[x].title;
                textDiv.append(text);
                div.append(imgDiv);
                div.append(textDiv);
                a.append(div)
                good_container.append(a);
                if((x+1)%5 == 0||x == json.length-1){
                    goodContain.append(good_container);
                    good_container = document.createElement("div");
                }
            }
        }
        let run = function(){
            let result = getJson();
            addElement(result);
        }
        return {
            run : run ,
        };
    }
)();