import React from 'react';
import Navigation from './navigation/Navigation.jsx';
import Body from "./main/Body.jsx";

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
            <div>

                <Navigation addComponentToView={this.addComponentToView}/>
                <Body/>

                {this.state.childs}
            </div>
        )
    }
}


