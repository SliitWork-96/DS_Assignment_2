const mongoose = require('mongoose');
const Schema = mongoose.Schema;

/* 
 * Sensor schema
 */
let Sensor = new Schema({

    SensorID: {
        type: String,
        required: true
    },

    FloorNo: {
        type: Number,
        required: true
    },

    roomNo: {
        type: Number,
        required: true
    },

    smokeLevel: {
        type: Number,
        default:0
    },

    Co2Level: {
        type: Number,
        default:0
    },

    status: {
        type: String,
        default:null
    }

},
{
    collection: 'Sensor'
});

module.exports = mongoose.model('Sensor',Sensor);
