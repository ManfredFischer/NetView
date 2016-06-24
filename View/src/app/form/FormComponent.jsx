import React from 'react';


class FormComponentText extends React.Component {

    constructor(props) {
        super(...arguments);
        this.state = {
            css: {
                width: props.width != null ? props.width : "100%",
                height: props.height != null ? props.height : 35,
                marginBottom: 5,
                borderRadius: props.borderRadius != null ? props.borderRadius : 5,
                borderColor: '#c1c1c1',
                margin: props.margin != null ? props.margin : "0 5 0 0",
                boxShadow: '',
                WebkitBoxShadow: '',
                padding: props.padding != null ? props.padding : "12px 20px",
            }
        }

        this.setText = this.setText.bind(this);
    }

    onFocusOrLostFocus(lost) {
        if (lost) {
            this.state.css.borderColor = '';
            this.state.css.boxShadow = '';
        } else {
            this.state.css.borderColor = '0 0 10px #9ecaed';
            this.state.css.boxShadow = '#9ecaed';
        }

        this.setState(this.state.css);
    }

    setText() {
        debugger
        var referenz = this.props.referenz;
        var data = {
            referenz : this.refs.input.value
        }
        this.props.addInformation(data)
    }

    render() {
        return (
            <input onFocus={this.onFocusOrLostFocus.bind(this,false)}
                   onLostFocus={this.onFocusOrLostFocus.bind(this,true)}
                   onChange={this.setText}
                   style={this.state.css}
                   ref="input"
                   placeholder={this.props.placeholder}>{this.props.value}</input>
        )
    }
}

class FormComponentArea extends React.Component {

    constructor(props) {
        super(props);

        var multipikator = 1;
        if (props.rows != null) {
            multipikator = parseInt(props.rows);
        }

        this.state = {
            css: {
                width: props.width != null ? props.width : "100%",
                height: props.height != null ? props.height * multipikator : 35 * multipikator,
                marginBottom: 5,
                borderRadius: props.borderRadius != null ? props.borderRadius : 5,
                borderColor: '#c1c1c1',
                margin: props.margin != null ? props.margin : "0 5 0 0",
                boxShadow: '',
                WebkitBoxShadow: '',
                padding: props.padding != null ? props.padding : "12px 20px",
            }
        }
    }

    onFocusOrLostFocus(lost) {
        if (lost) {
            this.state.css.borderColor = '';
            this.state.css.boxShadow = '';
        } else {
            this.state.css.borderColor = '0 0 10px #9ecaed';
            this.state.css.boxShadow = '#9ecaed';
        }

        this.setState(this.state.css);
    }


    getText() {
        return this.refs.textarea.value;
    }

    render() {
        return (
            <textarea onFocus={this.onFocusOrLostFocus.bind(this,false)}
                      onLostFocus={this.onFocusOrLostFocus.bind(this,true)}
                      style={this.state.css}
                      ref="textarea"
                      placeholder={this.props.placeholder}>{this.props.value}</textarea>
        )
    }
}


class FormComponentButton extends React.Component {

    constructor(props) {
        super(props);

        var multipikator = 1;
        if (props.rows != null) {
            multipikator = parseInt(props.rows);
        }

        this.state = {
            css: {
                width: props.width != null ? props.width : "100%",
                height: props.height != null ? props.height * multipikator : 35 * multipikator,
                marginBottom: 5,
                borderRadius: props.borderRadius != null ? props.borderRadius : 5,
                borderColor: '#c1c1c1',
                margin: props.margin != null ? props.margin : "0 5 0 0",
                boxShadow: '',
                WebkitBoxShadow: '',
                padding: props.padding != null ? props.padding : "12px 20px",
            }
        }
    }

    onFocusOrLostFocus(lost) {
        if (lost) {
            this.state.css.borderColor = '';
            this.state.css.boxShadow = '';
        } else {
            this.state.css.borderColor = '0 0 10px #9ecaed';
            this.state.css.boxShadow = '#9ecaed';
        }

        this.setState(this.state.css);
    }

    render() {
        return (
            <input type="button" onFocus={this.onFocusOrLostFocus.bind(this,false)}
                   onLostFocus={this.onFocusOrLostFocus.bind(this,true)}
                   style={this.state.css}
                   placeholder={this.props.placeholder}>{this.props.value}</input>
        )
    }
}
class FormComponentCheckbox extends React.Component {

    constructor(props) {
        super(props);

        var multipikator = 1;
        if (props.rows != null) {
            multipikator = parseInt(props.rows);
        }

        this.state = {
            css: {
                width: props.width != null ? props.width : "100%",
                height: props.height != null ? props.height * multipikator : 35 * multipikator,
                marginBottom: 5,
                borderRadius: props.borderRadius != null ? props.borderRadius : 5,
                borderColor: '#c1c1c1',
                margin: props.margin != null ? props.margin : "0 5 0 0",
                boxShadow: '',
                WebkitBoxShadow: '',
                padding: props.padding != null ? props.padding : "12px 20px",
            }
        }
    }

    onFocusOrLostFocus(lost) {
        if (lost) {
            this.state.css.borderColor = '';
            this.state.css.boxShadow = '';
        } else {
            this.state.css.borderColor = '0 0 10px #9ecaed';
            this.state.css.boxShadow = '#9ecaed';
        }

        this.setState(this.state.css);
    }

    render() {
        return (
            <input type="checkbox" onFocus={this.onFocusOrLostFocus.bind(this,false)}
                   onLostFocus={this.onFocusOrLostFocus.bind(this,true)}
                   style={this.state.css}
                   placeholder={this.props.placeholder}>{this.props.value}</input>
        )
    }
}

class FormComponentRadio extends React.Component {

    constructor(props) {
        super(props);

        var multipikator = 1;
        if (props.rows != null) {
            multipikator = parseInt(props.rows);
        }

        this.state = {
            css: {
                width: props.width != null ? props.width : "100%",
                height: props.height != null ? props.height * multipikator : 35 * multipikator,
                marginBottom: 5,
                borderRadius: props.borderRadius != null ? props.borderRadius : 5,
                borderColor: '#c1c1c1',
                margin: props.margin != null ? props.margin : "0 5 0 0",
                boxShadow: '',
                WebkitBoxShadow: '',
                padding: props.padding != null ? props.padding : "12px 20px",
            }
        }
    }

    onFocusOrLostFocus(lost) {
        if (lost) {
            this.state.css.borderColor = '';
            this.state.css.boxShadow = '';
        } else {
            this.state.css.borderColor = '0 0 10px #9ecaed';
            this.state.css.boxShadow = '#9ecaed';
        }

        this.setState(this.state.css);
    }

    render() {
        return (
            <input type="radio" onFocus={this.onFocusOrLostFocus.bind(this,false)}
                   onLostFocus={this.onFocusOrLostFocus.bind(this,true)}
                   style={this.state.css}
                   placeholder={this.props.placeholder}>{this.props.value}</input>
        )
    }
}

class FormComponentCombobox extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            css: {
                width: props.width != null ? props.width : "100%",
                height: props.height != null ? props.height : 35,
                marginBottom: 5,
                borderRadius: props.borderRadius != null ? props.borderRadius : 5,
                borderColor: '#c1c1c1',
                margin: props.margin != null ? props.margin : "0 5 0 0",
                boxShadow: '',
                WebkitBoxShadow: '',
                padding: props.padding != null ? props.padding : "12px 20px",
            }
        }
    }

    render() {
        return (
            <select style={this.state.css}
                    placeholder={this.props.placeholder}> {this.props.child}</select>
        )
    }
}


export {FormComponentText, FormComponentArea,FormComponentButton,FormComponentCheckbox,FormComponentRadio,FormComponentCombobox};

