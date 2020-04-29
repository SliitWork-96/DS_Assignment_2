
// include the nodemailer module
const nodeMailer = require('nodemailer'); 
const texts = require('../constants/texts');
const emailConfig = texts.emailConfigure;


/* 
 * POST request calls to the sendEmail in EmailController class to send a email
 */
exports.sendEmail = (req, res, next) => {

    //get the request details from the body
    const {body} = req;
    const {
        FloorNb,
        roomNo,
        Co2Level,
        smokeLevel,
        email
    } = body;

    //setup sender email
    const Emailtransporter = nodeMailer.createTransport({
        service: 'gmail',
        auth: {
            user: emailConfig.Email,
            pass: emailConfig.password
        }
    });

    //setup reciver email
    let mailOption = {
        from : emailConfig.Email,
        to : email,
        subject : 'Warning\n',
        text : 'Please pay Attention\n'+
        FloorNb +' th floor room nb '+ roomNo +
        '\n CO2 level : ' + Co2Level +
        '\n Smoke level : ' + smokeLevel + '\n'
    };

   
    //send email
    Emailtransporter.sendMail(mailOption, (err,info) => {
            if(err){
                console.log(err);
            }else{
                console.log('Email Sent : ' + info.response);
                res.status(200).json({
                    message: 'email successfully send'
                })
            }
    });
}

