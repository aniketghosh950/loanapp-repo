import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Home from './components/Home';
import Login from './components/Login';
import Register from './components/Register';
import ViewLoans from './components/ViewLoans';
import ViewItems from './components/ViewItems';
import UserDashboard from './components/UserDashboard';
import ApplyLoan from './components/ApplyLoan';

const App = () => {
  return (
    <div className="App">
      <h1>Banking App</h1>
      <BrowserRouter>
      <Routes>
        <Route exact path="/" element={<Home/>}/>
        <Route exact path="/login" element={<Login/>}/>
        <Route exact path="/register" element={<Register/>}/>

        <Route path="/user/:id" element={<UserDashboard/>}/>
        <Route path="/user/:id/viewLoans" element={<ViewLoans/>}/>
        <Route path="/user/:id/viewItems" element={<ViewItems/>}/>
        <Route path="/user/:id/applyLoan" element={<ApplyLoan/>}/>
      </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
