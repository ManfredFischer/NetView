/**
 * Created by mf on 05.06.2016.
 */
import React from 'react';
import Translation from '../../data/Translation/Translation.js';

const cssStyle = {
    body :{
        backgroundColor : 'silver',
        margin : '10px',
        width : '100%',
        height : '100%'
    },
    inhalt :{
        backgroundColor : 'white',
        position : 'absolute',
        width : '70%',
        height : '0%'
    }
}

export default class extends React.Component
{
    render()
    {
        return (
            <div style={cssStyle.body}>
                <div style={cssStyle.inhalt}>
                    Inhalt
                </div>
            </div>
        )
    }
}