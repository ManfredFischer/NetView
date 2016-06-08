/**
 * Created by mf on 05.06.2016.
 */
import React from 'react';

const cssStyle = {
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

export default class User extends React.Component {

    render() {

        return (
            <div style={cssStyle.bodyStyle}>
                <div style={cssStyle.menue}>
                    <div style={cssStyle.menue.item}></div>
                    <div style={cssStyle.menue.item}></div>
                    <div style={cssStyle.menue.item}></div>
                </div>
                <div style={cssStyle.body}>
                    <div style={cssStyle.body.default}>Main</div>
                    <div style={cssStyle.body.extends}>Erweitert</div>
                    <div style={cssStyle.body.comment}>Comment</div>
                </div>
            </div >
        )
    }
}
