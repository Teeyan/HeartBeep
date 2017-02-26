/*
var http = require('http');

var server = http.createServer(function (req, res) {
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('Hello World\n');
}).listen(3000);

console.log("Server running at 3000");
*/

var express = require('express');
var bodyParser = require('body-parser');

/* start Express webapp */
var app = express();
require('./routes')(app);
app.set('port', 5000);
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');

app.use(bodyParser.json());

app.get('/', function(req, res) {
    res.render('index');
});

app.listen(5000);
