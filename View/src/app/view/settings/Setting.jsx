/**
 * Created by mf on 05.06.2016.
 */
import React from 'react';

const cssSetting = {
    bodyStyle: {
    },
    menue : {
        item : {
        }
    },
    body: {
        default: {
        },
        extends : {
            display : 'none'
        },
        comment : {
            display : 'none'
        },
        backgroundColor : 'black'
    }
}

export default class extends React.Component {

    render() {

        return (
            <div style={cssSetting.bodyStyle}>
                <div style={cssSetting.menue}>
                    <div style={cssSetting.menue.item}></div>
                    <div style={cssSetting.menue.item}></div>
                    <div style={cssSetting.menue.item}></div>
                </div>
                <div style={cssSetting.body}>
                    <div style={cssSetting.body.default}>Main</div>
                    <div style={cssSetting.body.extends}>Erweitert</div>
                    <div style={cssSetting.body.comment}>Comment</div>
                </div>
            </div >
        )
    }
}
