/**
 * Created by mf on 05.06.2016.
 */
import React from 'react';
import { FormGroup,FormControl,Col,ControlLabel } from 'react-bootstrap';
import Translation from '../../../data/Translation/Translation.js';

const cssStyle = {
    bodyStyle: {
        padding: 10
    },
    textfield: {
        marginBottom: 5
    }, textfieldPLZ: {
        width: '20%',
        float: 'left'
    }, textfieldOrt: {
        width: '79%',
        marginBottom: 5,
        float: 'right',
    }, formGroup: {
        margin: 5
    }
}

export default class Netz extends React.Component {

    render() {

        return (
            <div style={cssStyle.bodyStyle}>


            </div >
        )
    }
}
