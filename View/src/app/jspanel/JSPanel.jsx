import React from 'react';
import { Grid,Row,Col,code,Glyphicon } from 'react-bootstrap';

class JSPanel extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            data : [],
            showImage: true,
            data: {
                header: {
                    resize: "resize-full",
                    close: "remove-circle",
                    collapse: "collapse-up",
                    view: "menu-up",
                }
            },
            css: {
                button: {
                    header: {
                        paddingLeft: 10,
                        cursor: 'pointer'
                    },
                    toolbar: {
                        paddingLeft: 10,
                        cursor: 'pointer'
                    }
                },
                view: {
                    position: 'absolute',
                    margin: 0,
                    padding: 0,
                    zIndex: 99,
                    height: props.config.view.window.height != null ? props.config.view.window.height : 600,
                    width: props.config.view.window.width != null ? props.config.view.window.width : 300,
                    left: props.config.view.window.left != null ? props.config.view.window.left : 20,
                    top: props.config.view.window.top != null ? props.config.view.window.top : 60,
                },
                header: {
                    WebkitUserSelect: 'none',
                    MozUserSelect: 'none',
                    userSelect: 'none',
                    msUserSelect: 'none',
                    cursor: 'default',
                    textAlign: 'left',
                    boxShadow: '5px 5px',
                    verticalAlign: 'center',
                    overflow: 'hidden',
                    textOverflow: 'ellipsis',
                    backgroundColor: 'silver',
                    margin: 0,
                    padding: 10,
                    borderRadius: '10px 10px 0px 0px',
                    width: '100%',
                    height: 40,
                    borderStyle: 'hidden hidden hidden hidden'
                },
                toolbar: {
                    display: props.config.toolBar == 'false' ? 'none' : 'visible',
                    boxShadow: '5px 5px',
                    backgroundColor: 'silver',
                    borderRadius: 0,
                    margin: 0,
                    width: '100%',
                    height: 22,
                    borderStyle: 'inline black'
                },
                body: {
                    margin: 0,
                    boxShadow: '5px 5px',
                    borderRadius: '0px 0px 10px 10px',
                    width: '100%',
                    height: '100%',
                    backgroundColor: 'white',
                    borderStyle: 'hidden ridge ridge ridge'
                }
            }
        }

        // Drag & Drop Config
        this.onDragStart = this.onDragStart.bind(this);
        this.onDrag = this.onDrag.bind(this);

        // Menue Header Config
        this.onCloseView = this.onCloseView.bind(this);
        this.onViewUpView = this.onViewUpView.bind(this);
        this.onCollapsView = this.onCollapsView.bind(this);
        this.onMaximiseView = this.onMaximiseView.bind(this);
    }




    onDragStart(event) {
        if (!this.pX) {
            this.pX = event.pageX;
            this.py = event.pageY;
        }
    }

    onDrag(event) {
        if ((event.pageX != 0 || event.pageY != 0)) {
            this.state.css.view.left = this.state.css.view.left + event.pageX - this.pX;
            this.state.css.view.top = this.state.css.view.top + event.pageY - this.py;
            this.setState(this.state);
            this.pX = event.pageX;
            this.py = event.pageY;
        }
    }



    onCloseView() {
        this.state.showImage = false;
        this.setState(this.state);
    }

    onViewUpView() {
        if (this.state.data.header.view == "menu-up") {
            this.state.css.toolbar.visibility = "hidden";
            this.state.css.body.visibility = "hidden";
            this.state.data.header.view = "menu-down";
        } else {
            this.state.css.toolbar.visibility = "visible";
            this.state.css.body.visibility = "visible";
            this.state.data.header.view = "menu-up";
        }

        this.setState(this.state);
    }

    onCollapsView() {
        if (this.state.data.header.collapse == "collapse-up") {
            this.state.css.toolbar.visibility = "hidden";
            this.state.css.body.visibility = "hidden";
            this.state.data.header.collapse = "collapse-down";

            this.state.css.view.left = 0;
            this.state.css.view.top = (parseInt(window.innerHeight) - parseInt(this.state.css.header.height)) - 90;
        } else {
            this.state.css.toolbar.visibility = "visible";
            this.state.css.body.visibility = "visible";
            this.state.data.header.collapse = "collapse-up";
        }

        this.setState(this.state);
    }

    onMaximiseView() {
        if (this.state.data.header.resize == "resize-full") {
            this.state.data.header.resize = "resize-small";

            this.state.css.toolbar.visibility = "visible";
            this.state.css.body.visibility = "visible";
            this.state.css.view.top = -70;
            this.state.css.header.height = 40;
            this.state.css.view.width = window.innerWidth - 10;
            this.state.css.view.height = window.innerHeight - 80;
        } else {
            this.state.data.header.resize = "resize-full";
        }

        this.setState(this.state);
    }


    render() {
        return this.state.showImage ? (<div style={this.state.css.view}>
                <div onDragStart={this.onDragStart}
                     onDrag={this.onDrag}
                     style={this.state.css.header}>
                    <Row>
                        <Col xs={6} md={4}
                             style={{width:"50%"}}>{this.props.title != null ? this.props.title : 'new JSPanel'}</Col>
                        <Col xs={6} md={4} style={{width:"50%",textAlign:'right'}}>
                            <Glyphicon style={this.state.css.button.header} onClick={this.onViewUpView}
                                       glyph={this.state.data.header.view}/>
                            <Glyphicon style={this.state.css.button.header} onClick={this.onCollapsView}
                                       glyph={this.state.data.header.collapse}/>
                            <Glyphicon style={this.state.css.button.header} onClick={this.onMaximiseView}
                                       glyph={this.state.data.header.resize}/>
                            <Glyphicon style={this.state.css.button.header} onClick={this.onCloseView}
                                       glyph={this.state.data.header.close}/></Col>
                    </Row>
                </div>
                <div style={this.state.css.toolbar}>{this.props.toolsIcons}
                    <Glyphicon style={this.state.css.button.toolbar} onClick={this.props.actionSEND.bind(this,this.props.actionSENDKey)} glyph="floppy-disk"/>
                    <Glyphicon style={this.state.css.button.toolbar} glyph="floppy-remove"/>
                </div>
                <div style={this.state.css.body}>
                    {this.props.bodyInfo}
                </div>
            </div>
        ) : (<div></div>)
    }
}
;


export default JSPanel;

