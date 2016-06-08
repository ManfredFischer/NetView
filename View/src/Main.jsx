import React from 'react';
import Navigation from './app/navigation/Navigation.jsx';
import Body from "./app/main/Body.jsx";

export default class extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            childs: []
        }
        this.addComponentToView = this.addComponentToView.bind(this);
    }

    addComponentToView(children) {
        this.setState({
            childs: this.state.childs.concat(children)
        });
    }

    render() {
        return (
            <div style={{backgroundColor:'silver'}}>
                <Navigation addComponentToView={this.addComponentToView}/>
                <Body addComponentToView={this.addComponentToView}/>
                <div>
                    {this.state.childs}
                </div>
            </div>
        )
    }
}


