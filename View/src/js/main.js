import React from 'react';
import ReactDOM from 'react-dom';
import Main from '../Main.jsx';

const App = class insertComponent extends React.Component {
    render() {
        return (
            <Main />
        )
    }
}

ReactDOM.render(<App />, document.getElementById('App'))