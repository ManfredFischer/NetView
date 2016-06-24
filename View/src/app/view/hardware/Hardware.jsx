/**
 * Created by mf on 05.06.2016.
 */
import React from 'react';
import Translation from '../../../data/translation/Translation.js';
import {FormComponentText, FormComponentArea,FormComponentButton,FormComponentCombobox} from '../../form/FormComponent.jsx';


export default class Hardware extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            defaultField: [],
            extendField: [],
            inputField: {
                paddingTop: 25,
            },
            comment: {width: '98%'},
            bodyStyle: {width: '100%', height: '100%'},
            menue: {
                defaultValue: {
                    WebkitUserSelect: 'none',
                    MozUserSelect: 'none',
                    userSelect: 'none',
                    msUserSelect: 'none',
                    cursor: 'pointer',
                    width: '25%',
                    float: 'left',
                    height: 30,
                    padding: 5,
                    textAlign: 'center',
                    borderBottom: '1px solid #a1a1a1',
                    position: 'initial',
                    marginTop: 4,
                    borderTop: '1px solid #a1a1a1',
                    borderLeft: '1px solid #a1a1a1',
                    borderRight: '1px solid #a1a1a1',
                    borderTopLeftRadius: '1em',
                    borderTopRightRadius: '1em'
                },
                itemDefault: {
                    backgroundColor: 'Gray',
                    active: true
                },
                itemExtend: {
                    active: false
                },
                itemComment: {
                    active: false
                },
                itemProtokoll: {
                    active: false
                }
            },
            view: {},
            menueDiv: {height: 40},
            body: {
                default: {},
                layoutComment: {
                    border: '1px solid black',
                },
                extends: {
                    display: 'none'
                },
                comment: {
                    margin: 5,
                    display: 'none'
                },
                protokoll: {
                    margin: 50,
                    display: 'none'
                },
            }
        }

        Object.assign(this.state.menue.itemDefault, this.state.menue.defaultValue);
        Object.assign(this.state.menue.itemExtend, this.state.menue.defaultValue);
        Object.assign(this.state.menue.itemComment, this.state.menue.defaultValue);
        Object.assign(this.state.menue.itemProtokoll, this.state.menue.defaultValue);
        this.insertDefaultInputField = this.insertDefaultInputField.bind(this);

        this.insertDefaultInputField();


    }

    onClickNav(select) {
        this.state.body.default.display = 'none';
        this.state.body.extends.display = 'none';
        this.state.body.comment.display = 'none';
        this.state.body.protokoll.display = 'none';
        this.state.menue.itemDefault.active = false;
        this.state.menue.itemExtend.active = false;
        this.state.menue.itemComment.active = false;
        this.state.menue.itemProtokoll.active = false;
        this.state.menue.itemDefault.backgroundColor = 'white';
        this.state.menue.itemExtend.backgroundColor = 'white';
        this.state.menue.itemComment.backgroundColor = 'white';
        this.state.menue.itemProtokoll.backgroundColor = 'white';

        switch (select) {
            case 'default':
                this.state.body.default.display = 'inline';
                this.state.menue.itemDefault.active = true;
                this.state.menue.itemDefault.backgroundColor = 'gray';
                break;
            case 'extended':
                this.state.body.extends.display = 'inline';
                this.state.menue.itemExtend.backgroundColor = 'gray';
                this.state.menue.itemExtend.active = true;
                break;
            case 'comment':
                this.state.body.comment.display = 'inline';
                this.state.menue.itemComment.backgroundColor = 'gray';
                this.state.menue.itemComment.active = true;
                break;
            case 'protokoll':
                this.state.body.protokoll.display = 'inline';
                this.state.menue.itemProtokoll.backgroundColor = 'gray';
                this.state.menue.itemProtokoll.active = true;
                break;
        }

        this.setState(this.state);
    }

    onMouseOverMenue(select) {
        switch (select) {
            case 'default':
                if (!this.state.menue.itemDefault.active) {
                    this.state.menue.itemDefault.backgroundColor = 'silver';
                }
                break;
            case 'extended':
                if (!this.state.menue.itemExtend.active) {
                    this.state.menue.itemExtend.backgroundColor = 'silver';
                }
                break;
            case 'comment':
                if (!this.state.menue.itemComment.active) {
                    this.state.menue.itemComment.backgroundColor = 'silver';
                }
                break;
                ;
            case 'protokoll':
                if (!this.state.menue.itemComment.active) {
                    this.state.menue.itemProtokoll.backgroundColor = 'silver';
                }
                break;
        }

        this.setState(this.state);
    }

    onLostFocusMenue(select) {
        switch (select) {
            case 'default':
                if (!this.state.menue.itemDefault.active) {
                    this.state.menue.itemDefault.backgroundColor = 'white';
                }
                break;
            case 'extended':
                if (!this.state.menue.itemExtend.active) {
                    this.state.menue.itemExtend.backgroundColor = 'white';
                }
                break;
            case 'comment':
                if (!this.state.menue.itemComment.active) {
                    this.state.menue.itemComment.backgroundColor = 'white';
                }
                break;
            case 'protokoll':
                if (!this.state.menue.itemComment.active) {
                    this.state.menue.itemProtokoll.backgroundColor = 'white';
                }
                break;
        }

        this.setState(this.state);
    }

    insertDefaultInputField() {

        for (var defaultData in Translation.Hardware.Data.Default) {
            this.state.defaultField.push(
                <tr>
                    <td> {Translation.Hardware.Data.Default[defaultData]}</td>
                    <td><FormComponentText/></td>
                </tr>
            );
        }

        for (var extendData in Translation.Hardware.Data.Extend) {
            this.state.extendField.push(
                <tr>
                    <td>{Translation.Hardware.Data.Extend[extendData]}</td>
                    <td><FormComponentText/></td>
                </tr>
            );
        }
    }

    render() {
        return (
            <div >
                <div style={this.state.menueDiv}>
                    <div onMouseOver={this.onMouseOverMenue.bind(this,'default')}
                         onClick={this.onClickNav.bind(this,'default')}
                         onMouseOut={this.onLostFocusMenue.bind(this,'default')}
                         style={this.state.menue.itemDefault}>{Translation.Hardware.default}</div>
                    <div onMouseOver={this.onMouseOverMenue.bind(this,'extended')}
                         onClick={this.onClickNav.bind(this,'extended')}
                         onMouseOut={this.onLostFocusMenue.bind(this,'extended')}
                         style={this.state.menue.itemExtend}>{Translation.Hardware.extended}</div>
                    <div onMouseOver={this.onMouseOverMenue.bind(this,'comment')}
                         onClick={this.onClickNav.bind(this,'comment')}
                         onMouseOut={this.onLostFocusMenue.bind(this,'comment')}
                         style={this.state.menue.itemComment}>{Translation.Hardware.comment}</div>
                    <div onMouseOver={this.onMouseOverMenue.bind(this,'protokoll')}
                         onClick={this.onClickNav.bind(this,'protokoll')}
                         onMouseOut={this.onLostFocusMenue.bind(this,'protokoll')}
                         style={this.state.menue.itemProtokoll}>{Translation.Hardware.protokoll}</div>
                </div>
                <div style={this.state.view}>
                    <div style={this.state.body.default}>
                        <table style={{width:'100%'}}>
                            <tr><td>IP-Adresse</td>
                            <td><FormComponentCombobox/></td>
                            </tr>
                            {this.state.defaultField}

                        </table>
                    </div>
                    <div style={this.state.body.extends}>
                        <table style={{width:'100%'}}>
                            {this.state.extendField}

                        </table>
                    </div>
                    <div style={this.state.body.comment}>
                        <FormComponentArea placeholder={Translation.Hardware.comment} rows="10" width="98%"/>
                    </div>
                    <div style={this.state.body.protokoll}>
                        <table style={{width:'100%'}}>
                            <tr>
                                <td>Eintrag</td>
                                <td><FormComponentText placeholder="Email"/></td>
                                <td><FormComponentButton/></td>
                            </tr>
                            <tr>
                                <td>
                                </td>
                                <td colSpan="2">
                                    <FormComponentArea placeholder={Translation.Hardware.comment} rows="7"
                                                       width="100%"/>
                                </td>
                            </tr>
                        </table>

                    </div>
                </div>
            </div>
        )
    }
}
