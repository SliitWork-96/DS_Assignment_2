const express = require('express');  
const cors = require('cors');
const mongoose = require('mongoose'); // help to connect mongodb database
const texts = require('./constants/texts');//take constant to prompt messages
const serverMessages = texts.server;

//create express server
const app = express();
const port = process.env.PORT || 5000;

//middlewares 
app.use(cors());  // cors middaleware
app.use(express.json()); // allows to get JSON


//connect to the mongoDB Atlas
mongoose.connect(serverMessages.MONGODB_URL,
{
    useNewUrlParser:true,useUnifiedTopology: true
})
.then(() =>{
    console.log(serverMessages.DB_CONNECTED);
})
.catch(()=>{
    console.log(serverMessages.DB_NOT_CONNECTED);
});

/* 
 * routes the request to the user route
 */
const userRoute = require('./routes/userRouter');
app.use('/user', userRoute);

/* 
 * routes the request to the sensor route
 */
const sensorRoute = require('./routes/SensorRouters');
app.use('/sensor', sensorRoute);

/* 
 * routes the request to the email Route
 */
const emailRoute = require('./routes/emailRouters');
app.use('/email', emailRoute);

/* 
 * routes the request to the sms Route
 */
const smsRoute = require('./routes/smsRouters');
app.use('/sms', smsRoute);


/* 
 * Backend server is lisenting to the port 5000 
 */
app.listen(port, () => {
    console.log(serverMessages.SERVER + port);
});
