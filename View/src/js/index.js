import React from 'react';
import ReactDOM from 'react-dom';
import Login from '../Login.jsx';


class App extends React.Component {
    render() {
        return (
            <Login />
        )
    }
}

ReactDOM.render(<App />, document.getElementById('index'))