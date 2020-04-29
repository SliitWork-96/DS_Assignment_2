import React, { Component } from 'react';
import axios from 'axios';
import { MDBTable, MDBTableBody, MDBTableHead } from 'mdbreact';
import TableRow from './details';
import {Card} from 'react-bootstrap';


class monitor extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            FireDetail : []
         }
    }

    componentDidMount(){
        this.getDetails();
        this.interval = setInterval(() => {
          this.getDetails();
        }, 40000);
    }

    getDetails() {
        axios.get('http://localhost:5000/sensor/')
        .then(response => {
            this.setState({ FireDetail: response.data });
        })
        .catch(function (error) {
            console.log(error);
        })
      }

    tabRow(){
        return this.state.FireDetail.map(function(object, i){
            return <TableRow obj={object} key={i} />;
        });
    }


    render() { 
        return ( 
            <Card className="text-center">
            <Card.Header style={{color:"red"}}><h5>Fire Sensor Monitor</h5></Card.Header>
            <Card.Body>
                <center>
                <MDBTable small style={{ marginTop: 20, width:"1000px" }}>
                    
                    <MDBTableHead>
                    <tr className="text-center">
                        <th>Sensor ID</th>
                        <th>Floor No</th>
                        <th>Room No</th>
                        <th>CO2 Level</th>
                        <th>Smoke Level</th>
                        <th>Status</th>
                        
                    </tr>
                    </MDBTableHead>
                    <MDBTableBody>
                    { this.tabRow() }
                    </MDBTableBody>
                
                </MDBTable>
                </center>
            </Card.Body>
            <Card.Footer className="text-muted"></Card.Footer>
            </Card>
         );
    }
}
 
export default monitor;