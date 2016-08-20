/**
 * Created by mf on 03.06.2016.
 */
import React from 'react';
import {FormComponentText,FormComponentArea} from '../../form/FormComponent.jsx';
import Translation from '../../../data/translation/Translation.js';

const cssStyle = {
    bodyStyle: {
        padding: 10
    },formGroup: {
        margin: 5
    }
}

export default class Location extends React.Component {

    render() {
        return (
            <div style={cssStyle.bodyStyle}>
                <FormComponentText keyInfo={this.props.keyInfo} setJSONData={this.props.setJSONData} referenz="company"   placeholder={Translation.Location.firmaPlaceholder}/>
                <FormComponentText keyInfo={this.props.keyInfo} setJSONData={this.props.setJSONData} referenz="street" placeholder={Translation.Location.streetPlaceholder}/>
                <FormComponentText keyInfo={this.props.keyInfo} setJSONData={this.props.setJSONData} referenz="plz" width='20%' placeholder={Translation.Location.PLZPlaceholder}/>
                <FormComponentText keyInfo={this.props.keyInfo} setJSONData={this.props.setJSONData} referenz="city" width='80%' placeholder={Translation.Location.placePlaceholder}/>
                <FormComponentArea keyInfo={this.props.keyInfo} setJSONData={this.props.setJSONData} referenz="description" rows='7' placeholder={Translation.Location.descriptionPlaceholder}/>
            </div >
        )
    }
}
