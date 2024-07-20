// import Carosel from "../../components/Layout/Carosel";
import Header from "../../components/Layout/Header";
import { Outlet } from "react-router-dom";

function Home() {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
}

export default Home;
