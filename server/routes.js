module.exports = function(app){
    var message = require('./controllers/message');
    app.get('/message', message.findAll);
    app.get('/message/:id', message.findById);
    app.post('/message', message.sendMessage);
    app.post('/message/heartbeat', message.heartbeat);
    app.put('/message', message.put);
    app.delete('/message/:id', message.delete);
};
