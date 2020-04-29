const mongoose = require("mongoose");
const bcrypt = require('bcrypt');
const Sensor = require('../model/sensor');

/* 
 * post request calls to the addSensor in SensorController class to insert a new Sensor 
 */
exports.addSensor = (req, res, next) => {

    const {body} = req;

    const {
        SensorID,
        FloorNo,
        roomNo,
    } = body;

/* 
 * check the sensor id already exist
 */
    Sensor.find({
        SensorID
    }).exec()
      .then(sensor => {

        if(sensor.length >= 1){
            return res.json({
                message : 'sensor already exist'
            });
        }else{

            /* 
             * Create the sensor schema
             */

            const newsensor = new Sensor();
            newsensor.SensorID = SensorID;
            newsensor.FloorNo = FloorNo;
            newsensor.roomNo = roomNo;
          
            /* 
             * Save the sensor schema
             */
            newsensor
                .save()
                .then(result => {
                    console.log(result);
                    res.status(200).json({
                        message: 'Sensor successfully created'
                    })
                })
                .catch(err => {
                    console.log(err);
                });

        }
    });
}

/* 
 * Get the all sensors
 */
exports.getAllSensor = (req, res) => {
    Sensor.find((err, sensor) => {
        if(err){
            console.log(err);
        }
        else {
            res.json(sensor);
        }
    });
}

/* 
 * Get the specific sensor
 */
exports.getSensor = (req, res) => {
    let sensorid = req.params.id;
    Sensor.findById(sensorid)
    .then(sensor => res.json(sensor))
    .catch(err => res.status(400).json('Error: ' + err));
}


/* 
 * PUT request calls to the editSensor in SensorController class to Update a existing sensor
 */
exports.editSensor = (req, res) => {

    const {body} = req;

    const {
        SensorID,
        FloorNo,
        roomNo,
        smokeLevel,
        Co2Level,
        status
    } = body;

    /* 
     * find the sensor
     */
    Sensor.findById(req.params.id, (err, sensor) => {
        if (!sensor)
            res.status(404).send({
                message:"sensor is not found"});
        else {
            sensor.SensorID = SensorID;
            sensor.FloorNo = FloorNo;
            sensor.roomNo = roomNo;
            sensor.smokeLevel = smokeLevel;
            sensor.Co2Level = Co2Level;
            sensor.status = status;

             /* 
              * update the sensor
              */
            sensor
            .save().then(sensor => {
                res.json({
                    message:'Update sensor complete'});
            })
            .catch(err => {
                res.status(400).send({
                    message:"unable to update the database"});
            });
        }
    });
}


exports.deleteSensor = (req,res,next) => {
    Sensor.remove({_id: req.params.id})
        .exec()
        .then(result => {
            res.status(200).json({
                message: "sensor deleted"
            });
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error:err
            });
        });
}