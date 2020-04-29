const nodeMailer = require('nodemailer');
const texts = require('../constants/texts');
const emailConfig = texts.emailConfigure;

/* 
 * POST request calls to the sendSms in SmsController class to send a sms
 */
exports.sendSms = (req, res, next) => {

    const {body} = req;
    const {
        FloorNb,
        roomNo,
        Co2Level,
        smokeLevel,
        email
    } = body;

    let message = "***** Warning ****** \n"+ "Please pay Attention \n" + 
                FloorNb + " th floor room nb "+ roomNo +
                '\n CO2 level : ' + Co2Level +
                '\n Smoke level : ' + smokeLevel + '\n'
  
    
    //print the sms in the console
    console.log(message);
    res.status(200).json({
        message: 'SMS successfully send'
    })
}

