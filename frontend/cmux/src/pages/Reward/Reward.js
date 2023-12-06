import React from "react";
import "./Reward.css";
import Widgets from "../../components/Widgets/Widgets";
import HomeBox from "../../components/HomeBox/HomeBox";
import Loading from "../../components/Loading/Loading";
import { useFetchWithTokenRefresh } from "../../utils/ApiUtilsDynamic";
import { useEffect, useState, useContext } from "react";
import { AuthContext } from "../../components/AuthProvider";
import ShopIcon from "../../components/ShopIcon/ShopIcon";

function Reward() {
  const [isAll, setIsAll] = React.useState(true);
  const [loading, setLoading] = React.useState(true);
  const [images, setImages] = useState([]);
  const [creditValue, setCreditValue] = useState();
  const [userProfile, setUserProfile] = useState();
  const [chosenProductId, setChosenProductId] = useState();
  const { userId, username } = useContext(AuthContext);
  const [availableImages, setAvailableImages] = useState([]);

  const fetchHelper = useFetchWithTokenRefresh();

  const filterProduct = () => {
    const filteredProducts = images.filter((product) =>
      userProfile.unlockedImageIds.includes(product.id)
    );
    setAvailableImages(filteredProducts);
  };

  const handleButton = (product) => {
    const purchasable = product.purchasable;
    if (purchasable) {
      return product.price > creditValue.coins;
    }
    return product.price > creditValue.points;
  };

  const handlePurchase = async () => {
    try {
      const url = `${process.env.REACT_APP_URL}shop/allProducts`;
      const response = await fetchHelper(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userId: userId,
          productId: chosenProductId,
        }),
      });

      const json = await response.json();

      if (response.ok) {
        console.log(json);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const fetchAllProducts = async () => {
    const url = `${process.env.REACT_APP_URL}shop/allProducts`;
    try {
      const response = await fetchHelper(url, {
        method: "GET",
      });
      if (response.ok) {
        const data = await response.json();
        setImages(data);
      }
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const fetchUserCredit = async () => {
    const url = `${process.env.REACT_APP_URL}shop/userCredit/${userId}`;
    try {
      const response = await fetchHelper(url, {
        method: "GET",
      });
      if (response.ok) {
        const credit = await response.json();
        setCreditValue(credit);
      }
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const fetchUserProfile = async () => {
    const url = `${process.env.REACT_APP_URL}user/${userId}`;
    try {
      const response = await fetchHelper(url, {
        method: "GET",
      });
      if (response.ok) {
        const profile = await response.json();
        setUserProfile(profile);
      }
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAllProducts();
    fetchUserCredit();
    fetchUserProfile();
    //filterProduct();
  }, []);

  if (loading) {
    return <Loading />;
  }
  return (
    <HomeBox>
      <div className="feed">
        <div>
          <div className="rewardTitle">
            {creditValue && (
              <>
                <p>Welcome to Reward System, {username}</p>
                <br />
                <br />
                <p>
                  You have {creditValue.coins} coins and {creditValue.points}{" "}
                  points
                </p>
              </>
            )}
          </div>
          <div className="rewardCategory">
            <div
              className={isAll && "rewardActive"}
              onClick={() => setIsAll(true)}
            >
              <span>Icon Shop</span>
            </div>
            <div
              className={!isAll && "rewardActive"}
              onClick={() => setIsAll(false)}
            >
              <span>View Credit History</span>
            </div>
          </div>
          <div>
            {isAll &&
            (
              <div className="productList">
                {images.map((product, index) => (
                  <ShopIcon
                    key={index}
                    product={product}
                    handleButton={handleButton(product)}
                    unit={product.purchasable ? "coins" : "points"}
                  ></ShopIcon>
                ))}
              </div>
            )}
            {!isAll && 
            (
              <div className="creditHistory"></div>
            )
            }
          </div>
        </div>
      </div>
      <Widgets />
    </HomeBox>
  );
}

export default Reward;
