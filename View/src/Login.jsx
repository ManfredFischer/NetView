/**
 * Created by mf on 05.06.2016.
 */
import React from 'react';
import {FormComponentText, FormComponentButton} from '../src/app/form/FormComponent.jsx';
import Translation from '../src/data/translation/Translation.js';

const cssStyle = {
    bodyStyle: {
        backgroundColor: '#D6D6D6',
        width: 300,
        height: 155,
        position: 'absolute',
        top: '40%',
        left: '40%',
        border: '1px solid',
        borderRadius: 10,
        padding: 8,
        boxShadow: '0px 1px 1px 1px',
    },
    header: {
        height: '20%',
        padding : 2
    },
    body: {
        backgroundColor : 'white',
        height: '80%',
        padding: 15,
    },
    labelText: {
        float: 'left',
        top: 2,
        width: '20%',
        height: 30,
        position: 'relative',
    },
    labelBody: {
        float: 'right',
        width: '65%',
        height: 30,
        position: 'relative',
    },
    bodyInfo : {
        height: '85%'
    },
    bodyReg : {
        height: '15%'
    }

}
class Login extends React.Component {
    render() {
        return (
            <div style={cssStyle.bodyStyle}>
                <div style={cssStyle.header}>
                    Anmeldung
                </div>
                <div style={cssStyle.body}>
                    <div style={cssStyle.bodyInfo}>
                    <div style={cssStyle.labelText}>Username:</div>
                    <div style={cssStyle.labelBody}><FormComponentText height="15" /></div>
                    <div style={cssStyle.labelText}>Passwor:</div>
                    <div style={cssStyle.labelBody}><FormComponentText height="15" /></div>
                    </div>
                    <div style={cssStyle.bodyReg}>
                         <a href="" >registrieren</a><FormComponentButton value="Login" float="right" width="50" height="22">Anmelden</FormComponentButton>
                    </div>
                </div>
            </div>
        )
    }
}

export default Login;