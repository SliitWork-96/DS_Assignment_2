import React, { Component } from 'react';
import {Alert} from 'react-bootstrap';
class details extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        return ( 
            <tr className="text-center">
            <td>
                {this.props.obj.SensorID}
            </td>
            <td>
                {this.props.obj.FloorNo}
            </td>
            <td>
                {this.props.obj.roomNo}
            </td>
            <td >
               {(this.props.obj.Co2Level > 5) ? 
                    <Alert variant="danger">
                        {this.props.obj.Co2Level}
                    </Alert> 
                    : 
                    <Alert variant="success ">
                        {this.props.obj.Co2Level}
                    </Alert> 
                    
                }
            </td>
            <td>
                {(this.props.obj.smokeLevel > 5) ? 
                    <Alert variant="danger">
                        {this.props.obj.smokeLevel}
                    </Alert> 
                    : 
                    <Alert variant="success ">
                        {this.props.obj.smokeLevel}
                    </Alert> 
                    
                }
                
            </td>
            <td>
                {this.props.obj.status}
            </td>
        
        </tr>
         );
    }
}
 
export default details;