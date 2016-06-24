import React from 'react';
import ReactDOM from 'react-dom';
import Translation from './src/data/translation/Translation.js';
import Main from './src/Main.jsx';


const App = class insertComponent extends React.Component {
    render() {
        return (
            <Main />
        )
    }
}

ReactDOM.render(<App />, document.getElementById('App'))