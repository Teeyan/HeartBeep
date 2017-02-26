var twilio = require('twilio');
var request = require('request');
var qs = require('querystring');
var http = require('http');
var TWILIO_ACCOUNT_SID = 'AC61437db34f6a4a54ea64d9e467bbff42',
    TWILIO_AUTH_TOKEN = '647db83509a69988ebeb05f0be635a55',
    TWILIO_NUMBER = '16467604943';

var client = twilio(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
var PHONE_NUMBER = '19176355777';

function heartattack() {
    request.post('http://localhost:5000/message/');
}

exports.findAll = function() {};
exports.findById = function() {};

exports.sendMessage = function(req, res) {
    console.log('Sending message');
    client.sendSms({
        to: PHONE_NUMBER,
        from: TWILIO_NUMBER,
        body: 'HeartBeep alert'
    }, function(err, data) {
        res.send("Sending message");
    });
};

exports.heartbeat = function(req, res) {
    console.log('Received POST request');
    if (this.timer == 'undefined') {
        this.timer = setTimeout(heartattack, 60000);
    } else {
        clearTimeout(this.timer);
        this.timer = setTimeout(heartattack, 60000);
    }

    var body = '';
    req.on('data', function(chunk) {
        body += chunk;
    });
    req.on('end', function() {
        var data = qs.parse(body);
        var csv = JSON.stringify(data).split(",");
        var numbers = csv[0].split(':');
        PHONE_NUMBER = numbers[1];
    });
};

exports.delete = function() {};
exports.put = function() {};
