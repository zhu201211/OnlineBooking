<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>时钟</title>

	<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath }/js/snow.js"></script>

	<style>
		* {
			margin: 0;
			padding: 0;
		}

		html, body {
			width: 100%;
			height: 100%;
			background: #666;
		}

		.snow-container {
			position: fixed;
			top: 0;
			left: 0;
			bottom: 0;
			right: 0;
			width: 100%;
			height: 100%;
			pointer-events: none;
			z-index: 100001;
			background: url(image/gg.jpg) center no-repeat;
			background-size: cover;
			z-index: 998;
		}

		.clock {
			/*box-sizing: border-box;*/
			width: 300px;
			height: 300px;
			background: rgba(0, 238, 255, 0.8);
			border: 1px solid;
			border-radius: 50%;
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			margin: auto;
		}

		.dot {
			width: 10px;
			height: 10px;
			border-radius: 50%;
			background: #fff;
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			margin: auto;
			z-index: 999;
		}

		.hour_wheel, .min_wheel, .sec_wheel {
			width: 100px;
			height: 6px;
			background: rgba(0, 0, 0, 0.5);
			border-radius: 5px;
			position: absolute;
			top: 147px;
			left: 140px;
			transform-origin: 10px center;
		}

		.min_wheel {
			width: 120px;
			height: 4px;
			top: 148px;
			background: rgba(186, 255, 0, 0.7);
		}

		.sec_wheel {
			width: 140px;
			height: 2px;
			top: 149px;
			background: rgba(242, 157, 35, 0.7);
		}

		.circle {
			width: 20px;
			height: 20px;
			background: #000;
			border-radius: 50%;
			color: #fff;
			font-size: 14px;
			line-height: 20px;
			text-align: center;
			position: absolute;
			top: 0;
			left: 0;
			z-index: 888;
		}

		.spot {
			width: 3px;
			height: 1px;
			background: #000;
			position: absolute;
			transform-origin: left center;
		}

		.out_ring {
			border: 1px solid;
			border-radius: 50%;
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			margin: auto;
			/*width: 300px;*/
			/*height: 300px;*/
		}

		.timebox {
			width: 100px;
			height: 30px;
			/*border: 1px solid #fff;*/
			border-radius: 4px;
			position: absolute;
			bottom: 30px;
			left: 0;
			right: 0;
			margin: auto;
			text-align: center;
			line-height: 30px;
			font-family: 'HappyZcool-201615507ac23b1d52e';
		}
	</style>
</head>
<body>
<div class="snow-container"></div>

<div class="clock">
	<div class="dot"></div>
	<div class="hour_wheel"></div>
	<div class="min_wheel"></div>
	<div class="sec_wheel"></div>
	<div class="out_ring"></div>
	<div class="timebox"></div>
</div>
</body>
<script>
    var clock = document.querySelector(".clock");
    var hourWheel = document.querySelector(".hour_wheel");
    var minWheel = document.querySelector(".min_wheel");
    var secWheel = document.querySelector(".sec_wheel");
    var outRing = document.querySelector(".out_ring");
    var timeBox = document.querySelector(".timebox");

    var R = clock.offsetHeight / 2;
    var PI = Math.PI;
    // sin@=y/R;
    // y = r*sin@;
    // y=Math.sin(2*PI/360*deg) * R;

    for (var i = 1; i <= 12; i++) {
        var circle = document.createElement("div");
        var deg = 30 * (i - 3);
        circle.className = "circle";
        circle.innerHTML = convert(i);
        clock.appendChild(circle);
        circle.style.left = Math.cos(2 * PI / 360 * deg) * R + R - circle.offsetHeight / 2 + "px";
        circle.style.top = Math.sin(2 * PI / 360 * deg) * R + R - circle.offsetHeight / 2 + "px";
    }

    for (var i = 1; i <= 60; i++) {
        var spot = document.createElement("div");
        var deg = 6 * i;
        spot.className = "spot";
        clock.appendChild(spot);
        spot.style.left = Math.cos(2 * PI / 360 * deg) * R - spot.offsetHeight / 2 + R - 1 + "px";
        spot.style.top = Math.sin(2 * PI / 360 * deg) * R - spot.offsetHeight / 2 + R - 1 + "px";
        spot.style.transform = `rotate(${deg}deg)`;
    }
    outRing.style.width = clock.offsetWidth + 2 * spot.offsetWidth + 2 + "px";
    outRing.style.height = clock.offsetHeight + 2 * spot.offsetWidth + 2 + "px";
    outRing.style.left = -2 * spot.offsetWidth + "px";

    showtime();

    function showtime () {
        var date = new Date();
        var hour = date.getHours() * 1 > 9 ? date.getHours() : "0" + date.getHours();
        var min = date.getMinutes() * 1 > 9 ? date.getMinutes() : "0" + date.getMinutes();
        var sec = date.getSeconds() * 1 > 9 ? date.getSeconds() : "0" + date.getSeconds();
        var hourDeg = hour * 30 + min / 60 * 30 - 90;
        var minDeg = min * 6 - 90;
        var secDeg = sec * 6 - 90;
        hourWheel.style.transform = "rotate(" + hourDeg + "deg)";
        minWheel.style.transform = "rotate(" + minDeg + "deg)";
        secWheel.style.transform = "rotate(" + secDeg + "deg)";
        timeBox.innerHTML = `${hour}:${min}:${sec}`;
    }

    setInterval(showtime, 1000);

    function convert (num) {
        var ans = "";
        var k = Math.floor(num / 1000);
        var h = Math.floor((num % 1000) / 100);
        var t = Math.floor((num % 100) / 10);
        var o = num % 10;
        var one = ["I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"];
        var ten = ["X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"];
        var hundred = ["C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"];
        var thousand = "M";
        for (var i = 0; i < k; i++) {
            ans += thousand;
        }
        if (h)
            ans += hundred[h - 1];
        if (t)
            ans += ten[t - 1];
        if (o)
            ans += one[o - 1];
        return ans;
    }
</script>
</html>