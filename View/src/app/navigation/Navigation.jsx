import React from 'react';
import { Row,Col,code,Navbar,Nav,NavItem,NavDropdown,MenuItem,Glyphicon,FormGroup,FormControl,Button } from 'react-bootstrap';
import JSPanel from '../../app/jspanel/JSPanel.jsx';
import Translation from '../../data/translation/Translation.js';

// Default BodyInfo
import Location from '../../app/view/location/Location.jsx';
import Hardware from '../../app/view/hardware/Hardware.jsx';
import Network from '../../app/view/network/Network.jsx';
import User from '../../app/view/settings/User.jsx';
import Setting from '../../app/view/settings/Setting.jsx';


import request from "../util/FetchFactory";
import requestData from '../util/RequestParam';

export default class extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: {}
        }
        this.setJSONData = this.setJSONData.bind(this);
    }

    setJSONData(keyInfo, data) {
        if (this.state.data[keyInfo] == null) {
            this.state.data[keyInfo] = {}
        }
        this.state.data[keyInfo][Object.getOwnPropertyNames(data)[0]] = data[Object.getOwnPropertyNames(data)[0]];

        this.setState({
            data: this.state.data
        })
    }

    addChild(component) {
        var bodyInfo, viewTitle;
        var me = this;
        var referenz = Date.now();

        var sendInfo = function (keyInfo) {
            debugger
            if (me.state.data[keyInfo] != null) {
                if (Object.keys(me.state.data[keyInfo]).length != '') {
                    request("/componente/location", requestData.POST, requestData.CONTENT_JSON, JSON.stringify(me.state.data[keyInfo]))
                        .then(token => {

                        }).catch(err => console.log(err))
                }
            }
        }
        var viewConfig = {
            view: {
                window: {}
            }
        }
        switch (component) {
            case 'setting':
                viewTitle = Translation.Settings.Setting.title;
                viewConfig.view.window.width = 600;
                viewConfig.view.window.height = 300;
                bodyInfo = <Setting />;
                break;
            case 'user':
                viewTitle = Translation.Settings.User.title;
                viewConfig.view.window.width = 600;
                viewConfig.view.window.height = 400;
                bodyInfo = <User />;
                break;
            case 'location':
                viewTitle = Translation.Location.createTitle;
                viewConfig.view.window.width = 600;
                viewConfig.view.window.height = 400;
                bodyInfo = <Location keyInfo={referenz} setJSONData={this.setJSONData}/>;
                break;
            case 'network':
                viewTitle = Translation.Network.createTitle;
                viewConfig.view.window.width = 600;
                viewConfig.view.window.height = 400;
                bodyInfo = <Network />;
                break;
            case 'hardware':
                viewTitle = Translation.Hardware.createTitle;
                viewConfig.view.window.width = 600;
                viewConfig.view.window.height = 400;
                bodyInfo = <Hardware />;
                break;

        }

        this.props.addComponentToView(<JSPanel actionSENDKey={referenz} actionSEND={sendInfo} config={viewConfig}
                                               title={viewTitle}
                                               bodyInfo={bodyInfo}/>);
    }

    searchChild() {

    }

    render() {
        return (

            <Navbar inverse fluid>
                <Navbar.Header>
                    <Navbar.Brand>
                        <Glyphicon glyph="menu-hamburger"/>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        <NavDropdown eventKey={1} title={Translation.Navigation.Menue.title} id="basic-nav-dropdown">
                            <MenuItem itemID="import" onClick={this.addChild.bind(this,"import")}
                                      eventKey={1.1}><Glyphicon
                                glyph="import"/>{Translation.Navigation.Menue.import} </MenuItem>
                            <MenuItem onClick={this.addChild.bind(this,"export")} eventKey={1.2}><Glyphicon
                                glyph="export"/> {Translation.Navigation.Menue.export}</MenuItem>
                            <MenuItem onClick={this.addChild.bind(this,"setting")} eventKey={1.3}><Glyphicon
                                glyph="cog"/> {Translation.Navigation.Menue.settings}</MenuItem>
                            <MenuItem onClick={this.addChild.bind(this,"user")} eventKey={1.4}><Glyphicon glyph="user"/>
                                {Translation.Navigation.Menue.newUser}</MenuItem>
                            <MenuItem divider/>
                            <MenuItem eventKey={1.5}><Glyphicon glyph="log-out"/> {Translation.Navigation.Menue.logout}
                            </MenuItem>
                        </NavDropdown>
                        <NavDropdown eventKey={2} title={Translation.Navigation.Network.title}
                                     id="basic-nav-dropdown">
                            <MenuItem onClick={this.addChild.bind(this,"location")} eventKey={2.1}><Glyphicon
                                glyph="plus"/> {Translation.Location.createTitle}</MenuItem>
                            <MenuItem onClick={this.addChild.bind(this,"network")} eventKey={3.2}><Glyphicon
                                glyph="plus"/> {Translation.Network.createTitle}</MenuItem>
                            <MenuItem onClick={this.addChild.bind(this,"hardware")} eventKey={3.3}><Glyphicon
                                glyph="plus"/> {Translation.Hardware.createTitle}</MenuItem>
                            <MenuItem divider/>
                            <MenuItem onClick={this.addChild.bind(this,"search")} eventKey={3.3}><Glyphicon
                                glyph="search"/> {Translation.Navigation.Network.search}</MenuItem>
                        </NavDropdown>
                    </Nav>
                    <Nav pullRight>
                        <NavItem eventKey={1} href="#" onClick={this.addChild.bind(this,"import")}><Glyphicon
                            glyph="user"/> {this.props.username}</NavItem>
                    </Nav>
                    <Navbar.Form pullRight>
                        <FormGroup>
                            <FormControl type="text" placeholder={Translation.Default.searchPlaceholder}/>
                        </FormGroup>
                        <Button onClick={this.searchChild.bind(this)}
                                type="submit">{Translation.Default.search}</Button>
                    </Navbar.Form>

                </Navbar.Collapse>
            </Navbar>
        )
    }
};

