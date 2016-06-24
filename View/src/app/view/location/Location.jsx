/**
 * Created by mf on 03.06.2016.
 */
import React from 'react';
import {FormComponentText,FormComponentArea} from '../../form/FormComponent.jsx';
import Translation from '../../../data/translation/Translation.js';
import { findDOMNode } from 'react-dom';
import request from "../../util/FetchFactory";
import requestData from '../../util/RequestParam';

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

export default class Location extends React.Component {
    constructor(props) {
        super(props);
        this.state = { info :"test"}
        this.getInformationAsJSON = this.getInformationAsJSON.bind(this);
        this.sendInformation = this.sendInformation.bind(this);
    }

    sendInformation() {
        debugger
        request("/componente/location", requestData.POST,this.getInformationAsJSON(),requestData.CONTENT_JSON)
            .then(token => {

            }).catch(err => console.log(err))
    }

    getInformationAsJSON() {
        return {
            company : this.refs.company.getText(),
            street : this.refs.street.getText(),
            plz : this.refs.plz.getText(),
            city : this.refs.city.getText(),
            description : this.refs.description.getText(),

        };
    }

    render() {
        return (
            <div style={cssStyle.bodyStyle}>
                <FormComponentText addInformation={this.props.jsonData} referenz="company"   placeholder={Translation.Location.firmaPlaceholder}/>
                <FormComponentText addInformation={this.props.jsonData} referenz="street" placeholder={Translation.Location.streetPlaceholder}/>
                <FormComponentText addInformation={this.props.jsonData} referenz="plz" width='20%' placeholder={Translation.Location.PLZPlaceholder}/>
                <FormComponentText addInformation={this.props.jsonData} referenz="city" width='80%' placeholder={Translation.Location.placePlaceholder}/>
                <FormComponentArea addInformation={this.props.jsonData} referenz="description" rows='7' placeholder={Translation.Location.descriptionPlaceholder}/>
                <div><button onClick={this.sendInformation}>Test</button></div>
            </div >
        )
    }
}
