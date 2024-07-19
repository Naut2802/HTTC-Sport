import carosel_1 from '~/components/Images/carosel-1.jpg';
import carosel_2 from '~/components/Images/carosel-2.jpg';
import carosel_3 from '~/components/Images/carosel-3.jpg';
import carosel_4 from '~/components/Images/carosel-4.jpg';

export default function Banner() {
    return (
        <div id="carouselExampleAutoplaying" className="carousel slide" data-bs-ride="carousel">
            <div className="carousel-inner rounded-4">
                <div className="carousel-item active">
                    <img src={carosel_1} className="d-block w-100 img-fluid" alt="" />
                </div>
                <div className="carousel-item active">
                    <img src={carosel_2} className="d-block w-100 img-fluid" alt="" />
                </div>
                <div className="carousel-item active">
                    <img src={carosel_3} className="d-block w-100 img-fluid" alt="" />
                </div>
                <div className="carousel-item active">
                    <img src={carosel_4} className="d-block w-100 img-fluid" alt="" />
                </div>
            </div>
            <button
                className="carousel-control-prev"
                type="button"
                data-bs-target="#carouselExampleAutoplaying"
                data-bs-slide="prev"
            >
                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Previous</span>
            </button>
            <button
                className="carousel-control-next"
                type="button"
                data-bs-target="#carouselExampleAutoplaying"
                data-bs-slide="next"
            >
                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Next</span>
            </button>
        </div>
    );
}
