/**
 * Created by mf on 03.06.2016.
 */
import React from 'react';
import {FormComponentText,FormComponentArea} from '../../../data/Form/FormComponent.jsx';
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

export default class Location extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {

        return (
            <div style={cssStyle.bodyStyle}>
                <FormComponentText placeholder={Translation.Location.firmaPlaceholder}/>
                <FormComponentText placeholder={Translation.Location.streetPlaceholder}/>
                <FormComponentText width='20%' placeholder={Translation.Location.PLZPlaceholder}/>
                <FormComponentText width='80%' placeholder={Translation.Location.placePlaceholder}/>
                <FormComponentArea rows='7' placeholder={Translation.Location.descriptionPlaceholder}/>
            </div >
        )
    }
}
